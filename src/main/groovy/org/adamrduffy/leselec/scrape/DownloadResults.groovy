package org.adamrduffy.leselec.scrape

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.domain.Result
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.ccil.cowan.tagsoup.Parser

class DownloadResults {

    public static final String IEC_URL = "http://www.iec.org.ls/results%202017/"

    static void downloadResultFile(District district, Result result, String downloadFolder) {
        def url = new URL(IEC_URL + result.url)
        def name = FilenameUtils.getName(url.getPath())
        def folder = downloadFolder + File.separator + district.name
        new File(folder).mkdirs()
        def file = new File(folder, name)
        FileUtils.copyURLToFile(encodeParameters(url), file)
        result.file = folder + File.separator + name
    }

    static List<Result> extractResultUrls(District district) {
        def tagsoupParser = new Parser()
        def slurper = new XmlSlurper(tagsoupParser)
        def htmlParser = slurper.parse(encodeParameters(new URL(IEC_URL + district.url)).toString())

        List<Result> results = new ArrayList<>()
        htmlParser.'**'.findAll{ it.name() == "a" }.each { link ->
            if (StringUtils.equalsIgnoreCase(link.text(), "Download")) {
                results.add(new Result(url: link.@href.text()))
            }
        }
        return results
    }

    static List<District> extractDistrictUrls(String url = IEC_URL) {
        def tagsoupParser = new Parser()
        def slurper = new XmlSlurper(tagsoupParser)
        def htmlParser = slurper.parse(url)

        List<District> districts = new ArrayList<>()
        htmlParser.'**'.findAll{ it.name() == "a" }.each { link ->
            districts.add(new District(name: link.text(), url: link.@href.text()))
        }
        return districts
    }

    static URL encodeParameters(URL url) {
        if (url.query != null) {
            def queryString = url.query.split("&").collect { splitQueryParameter(it) }
            url.query = StringUtils.join(queryString.collect { query -> StringUtils.join(query, "=") }, "&")
        }
        return url
    }

    static def splitQueryParameter(String it) {
        final int idx = it.indexOf("=")
        final String key = idx > 0 ? it.substring(0, idx) : it
        final String value = idx > 0 && it.length() > idx + 1 ? URLEncoder.encode(it.substring(idx + 1), "UTF-8") : null
        return [key, value]
    }

    static save(Object content, String filePath) {
        new File(filePath).write(new JsonBuilder(content).toPrettyString())
    }

    static Object load(String filePath) {
        return new JsonSlurper().parseText(new File(filePath).text)
    }

    static void main(String[] args) {
        def districts = extractDistrictUrls()
        districts = districts.findAll{ StringUtils.isNotBlank(it.name) && !StringUtils.equalsIgnoreCase(it.name, "Home")}
        districts.each { district ->
            def pattern = (district.name =~ /(.+)\s*?\((\d+).*\)/)
            district.name = StringUtils.trim(pattern[0][1] as String)
            district.resultCount = NumberUtils.toInt(pattern[0][2] as String)
            district.results = extractResultUrls(district)
            district.results.each { result ->
                downloadResultFile(district, result, "results" + File.separator + "2017")
            }
        }

        save(districts, "districts.json")
    }

}

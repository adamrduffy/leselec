package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.domain.PartyColour
import org.adamrduffy.leselec.json.JsonFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named
class PartyColoursService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartyColoursService.class)

    private List<PartyColour> partyColours

    List<PartyColour> read() {
        return partyColours
    }

    @PostConstruct
    void load() {
        LOGGER.info("reading json file")
        ClassPathResource partyColoursJson = new ClassPathResource("party.colours.json")
        LOGGER.info("parsing json file and creating objects")
        partyColours = JsonFile.load(partyColoursJson.inputStream)
    }
}

package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.domain.Seats
import org.adamrduffy.leselec.json.JsonFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named
class SeatsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeatsService.class)

    private Seats seats

    Seats read() {
        return seats
    }

    @PostConstruct
    void load() {
        LOGGER.info("reading json file")
        ClassPathResource seatsJson = new ClassPathResource("seats.json")
        LOGGER.info("parsing json file and creating objects")
        seats = JsonFile.load(seatsJson.inputStream)
    }
}

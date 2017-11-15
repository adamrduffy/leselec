package org.adamrduffy.leselec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StatusController {

    @RequestMapping("/status")
    String status() {
        return "up"
    }
}

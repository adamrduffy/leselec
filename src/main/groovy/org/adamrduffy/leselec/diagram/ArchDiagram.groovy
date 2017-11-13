package org.adamrduffy.leselec.diagram

import org.adamrduffy.leselec.domain.Party
import org.adamrduffy.leselec.json.JsonFile

/**
 * Adapted from https://github.com/slashme/parliamentdiagram/blob/master/newarch.py
 */
class ArchDiagram {
    static final int[] TOTALS = [3, 15, 33, 61, 95, 138, 189, 247, 313, 388, 469, 559, 657, 762, 876, 997, 1126, 1263,
                                 1408, 1560, 1722, 1889, 2066, 2250, 2442, 2641, 2850, 3064, 3289, 3519, 3759, 4005,
                                 4261, 4522, 4794, 5071, 5358, 5652, 5953, 6263, 6581, 6906, 7239, 7581, 7929, 8287,
                                 8650, 9024, 9404, 9793, 10187, 10594, 11003, 11425, 11850, 12288, 12729, 13183, 13638,
                                 14109, 14580, 15066, 15553, 16055, 16557, 17075, 17592, 18126, 18660, 19208, 19758,
                                 20323, 20888, 21468, 22050, 22645, 23243, 23853, 24467, 25094, 25723, 26364, 27011,
                                 27667, 28329, 29001, 29679, 30367, 31061]

    static String generate(List<Party> parties, int delegates = 120) {
        List<Party> electedParties = parties.findAll { party -> party.totalSeats > 0 }

        int rows = 0
        for (; rows < TOTALS.length && delegates > TOTALS[rows]; rows++) {
            // do nothing
        }
        rows += 1

        double radius = 0.4 / rows
        List poslist = generateSeatPositionsInDiagram(rows, delegates, radius)

        StringBuffer stringBuffer = new StringBuffer()
        stringBuffer << "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        stringBuffer << "<svg xmlns:svg=\"http://www.w3.org/2000/svg\"\n"
        stringBuffer << "     xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"\n"
        stringBuffer << "     width=\"360\" height=\"185\">\n"
        stringBuffer << "<g>\n"
        stringBuffer << "    <text x=\"175\" y=\"175\" style=\"font-size:36px;font-weight:bold;text-align:center;text-anchor:middle;font-family:sans-serif\">$delegates</text>\n"

        int totalCounter = 0
        for (party in electedParties) {
            def color = Party.getColor(party.code)
            stringBuffer << generatePartySeatGroup(party.code, color, totalCounter, radius, party.totalSeats, poslist)
            totalCounter += party.totalSeats
        }
        stringBuffer << generatePartySeatGroup("EMPTY", "#000000", totalCounter, radius, delegates - totalCounter, poslist)

        stringBuffer << "</g>\n"
        stringBuffer << "</svg>\n"

        return stringBuffer.toString()
    }

    private static List generateSeatPositionsInDiagram(int rows, int delegates, double radius) {
        def poslist = []
        for (int i in 1..rows - 1) {
            int J = delegates / TOTALS[rows - 1] * Math.PI / (2 * Math.asin(2.0 / (3.0 * rows + 4.0 * i - 2.0))) as int
            double R = (3.0 * rows + 4.0 * i - 2.0) / (4.0 * rows)
            generateSeatPositionsInRow(J, poslist, R, radius)
        }

        // the last row with the left over seats
        int J = delegates - poslist.size()
        def R = (7.0 * rows - 2.0) / (4.0 * rows)
        generateSeatPositionsInRow(J, poslist, R, radius)
        // sort by position so that party seats are grouped together
        return poslist.sort { a, b -> a[0] <=> b[0] }.reverse()
    }

    private static void generateSeatPositionsInRow(int J, List poslist, double R, double radius) {
        if (J == 1) {
            poslist << ([Math.PI / 2.0, 1.75 * R, R])
        } else {
            for (int j in 0..J - 1) {
                double angle = j * (Math.PI - 2.0 * Math.sin(radius / R)) / (J - 1.0) + Math.sin(radius / R)
                poslist << ([angle, R * Math.cos(angle) + 1.75, R * Math.sin(angle)])
            }
        }
    }

    static String generatePartySeatGroup(String code, String color, int totalCounter, double radius, int totalSeats, def poslist) {
        StringBuffer stringBuffer = new StringBuffer()
        stringBuffer << "    <g style=\"fill:$color\" id=\"$code\">\n"
        for (int counter = totalCounter; counter < totalCounter + totalSeats; counter++) {
            stringBuffer << sprintf("        <circle cx=\"%.2f\" cy=\"%.2f\" r=\"%.2f\"/>\n", poslist[counter][1] * 100.0 + 5.0, 100.0 * (1.75 - (poslist[counter][2] as double)) + 5.0, radius * 100.0)
        }
        stringBuffer << "    </g>\n"
        return stringBuffer.toString()
    }

    static void main(String[] args) {
        def seats = JsonFile.load("seats.json")
        def svg = new File("parliament.svg")
        svg.write generate(seats.parties as List<Party>)
    }
}

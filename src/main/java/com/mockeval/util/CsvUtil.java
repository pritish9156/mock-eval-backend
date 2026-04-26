package com.mockeval.util;

import com.mockeval.entity.Evaluation;
import java.io.PrintWriter;
import java.util.List;

public class CsvUtil {

    public static void writeToCsv(PrintWriter writer, List<Evaluation> list) {

        writer.println("Participant,Score,Feedback");

        for (Evaluation e : list) {
            writer.println(
                    e.getAssignment().getParticipant().getName() + "," +
                            e.getScore() + "," +
                            e.getFeedback()
            );
        }
    }
}
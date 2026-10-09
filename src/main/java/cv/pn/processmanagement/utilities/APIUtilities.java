package cv.pn.processmanagement.utilities;

import cv.pn.processmanagement.exceptions.RecordNotFoundException;

import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.regex.Pattern;

public class APIUtilities {

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    public static final String RECORD_NOT_FOUND = "RECORD NOT FOUND";
    public static final String VALIDATION_FAILED = "VALIDATION FAILED";


    public static <T> Optional<T> checkResource(Optional<T> resource, String message){
        if(!resource.isPresent())
            throw new RecordNotFoundException(message);

        return resource;
    }


    public static String replaceSpecialCharacters(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("[^\\p{ASCII}]");
        normalizedString = pattern.matcher(normalizedString).replaceAll("");
        normalizedString = normalizedString.toLowerCase();
        return normalizedString;
    }

    public static String generatorOccurrenceCode(String organicCode){

        LocalDateTime dateTime = LocalDateTime.now();

       // DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        //return "PN".concat(organicCode).concat(dateTime.format(format)).toUpperCase().trim();
        return "PN".concat(dateTime.format(format).trim()).concat("/").concat(organicCode).toUpperCase().trim();

    }


    public static String generatorRecordCode(String sequencia, String organicCode, String occurrence){

        //   001/29092023/SPF

        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyy");

        //return sequencia.concat("/").concat(dateTime.format(format)).concat("/").concat(organicCode).toUpperCase().trim();

        return sequencia.concat("/").concat(occurrence.split("/")[0]).concat("/").concat(organicCode).toUpperCase().trim();
    }


    public static String selectOccurrenceColor(LocalDateTime creationDate) {

        LocalDateTime now = LocalDateTime.now();

        long hoursSinceCreation = ChronoUnit.HOURS.between(creationDate, now);

        if (hoursSinceCreation <= 24) {
            return "light";
        } else if (hoursSinceCreation <= 36) {
            return "badge-info";
        } else if (hoursSinceCreation <= 48) {
            return "badge-warning";
        } else {
            return "badge-danger";
        }
    }

    public static String selectRemainingOccurrenceDate(LocalDateTime creationDate) {

        LocalDateTime dataAtual = LocalDateTime.now();

        Duration tempoPassado = Duration.between(creationDate, dataAtual);
        Duration tempoTotal = Duration.ofHours(48);

        Duration tempoRestante = tempoTotal.minus(tempoPassado);

        if (tempoRestante.isNegative()) {
            tempoRestante = Duration.ZERO;
        }

        long horas = tempoRestante.toHours();
        long minutos = tempoRestante.toMinutes() % 60;
        long segundos = tempoRestante.getSeconds() % 60;

        return horas + ":" + minutos + ":" + segundos;
    }

    public static String selectCurrentOccurrenceDate(LocalDateTime creationDate) {

        Duration diferenca = Duration.between(creationDate, LocalDateTime.now());

        long horas = diferenca.toHours();

        long minutos = diferenca.toMinutes() % 60;

        long segundos = diferenca.getSeconds() % 60;

        return horas + ":" + minutos + ":" + segundos;
    }

    public static String formatDate(LocalDateTime date) {

        if (date == null) {
            return null;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return date.format(format);
    }

    public static String formatLocalDateddMMyyyy(LocalDate date) {

        if (date == null) {
            return null;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return date.format(format);
    }

    public static String formatHour(LocalDateTime date) {

        if (date == null) {
            return null;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

        return date.format(format);
    }


    public static String formatDateAndHour(LocalDateTime date) {

        if (date == null) {
            return null;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return date.format(format);
    }

    public static String calcularIdade(LocalDate birthDate) {
        // Define o formato da data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Converte a string da data de nascimento em um objeto LocalDate
        //LocalDate birthDate = LocalDate.parse(birthDateString, formatter);

        // Obtém a data atual
        LocalDate currentDate = LocalDate.now();

        // Calcula o período entre a data de nascimento e a data atual
        Period age = Period.between(birthDate, currentDate);

        // Formata a idade em anos, meses e dias
        return String.valueOf(age.getYears());
    }


}



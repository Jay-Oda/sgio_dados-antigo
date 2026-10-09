package cv.pn.processmanagement.integrations.notification.templates;

import cv.pn.processmanagement.integrations.notification.SendEmailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailFactory {

    @Value("${notification.email.default-email-sender}")
    private String defaultEmailSender;
    @Value("${notification.email.default-origin}")
    private String emailOrigin;

    private static final String EMAIL_TEMPLATE = "Prezado(a), a sua denúncia com o Número de Ocorrência: %s, foi enviada ao Ministério Público.";

    private static final String ACTOR_OCCURRANCE_REGISTRATION_EMAIL_TEMPLATE = "Prezado(a), a sua denúncia foi registada com o Número de Ocorrência: %s.\n" +
            "Favor, faça acompanhar do respetivo  Número de Ocorrência, para melhor localizar o estado da sua denúncia nas pendencias policiais.";

    private static final String ACTOR_OCCURRENCE_REFUSED = "Prezado(a), queira encontrar no seu ambiente de registo a ocorrência Número de Ocorrência: %s, devolvida para correção.";

    public SendEmailDTO getMPOccurenceDispatchEmailContent(String recipient, String occurrenceNumber) {
        String message = String.format(EMAIL_TEMPLATE, occurrenceNumber);
        SendEmailDTO sendEmailDTO = new SendEmailDTO();
        sendEmailDTO.setMsg(message);
        sendEmailDTO.setSubject("Notificação");
        sendEmailDTO.setFrom(defaultEmailSender);
        sendEmailDTO.setMyto(recipient);
        sendEmailDTO.setOrigin(emailOrigin);
        return sendEmailDTO;
    }

    public SendEmailDTO getActorOccurrenceRegistrationNotification(String recipient, String occurrenceNumber) {
        String message = String.format(ACTOR_OCCURRANCE_REGISTRATION_EMAIL_TEMPLATE, occurrenceNumber);
        SendEmailDTO sendEmailDTO = new SendEmailDTO();
        sendEmailDTO.setMsg(message);
        sendEmailDTO.setSubject("Notificação");
        sendEmailDTO.setFrom(defaultEmailSender);
        sendEmailDTO.setMyto(recipient);
        sendEmailDTO.setOrigin(emailOrigin);
        return sendEmailDTO;
    }

    public SendEmailDTO getActorOccurrenceRefusedNotification(String recipient, String occurrenceNumber) {
        String message = String.format(ACTOR_OCCURRENCE_REFUSED, occurrenceNumber);
        SendEmailDTO sendEmailDTO = new SendEmailDTO();
        sendEmailDTO.setMsg(message);
        sendEmailDTO.setSubject("Notificação");
        sendEmailDTO.setFrom(defaultEmailSender);
        sendEmailDTO.setMyto(recipient);
        sendEmailDTO.setOrigin(emailOrigin);
        return sendEmailDTO;
    }
}

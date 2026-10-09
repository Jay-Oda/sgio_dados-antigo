package cv.pn.processmanagement.integrations.notification.templates;

import cv.pn.processmanagement.integrations.notification.SendSMSDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsFactory {

    @Value("${notification.sms.default-origin}")
    private String defaultOrigin;
    @Value("${notification.sms.default-source}")
    private String defaultSource;

    private static final String SMS_REPORTER_NOTIFICATION_TEMPLATE = "Prezado(a), a sua denúncia com o Número de Ocorrência: %s, foi enviada ao Ministério Público.";

    private static final String ACTOR_OCCURRANCE_REGISTRATION_EMAIL_TEMPLATE = "Prezado(a), a sua denúncia foi registada com o Número de Ocorrência: %s.\n" +
            "Favor, faça acompanhar do respetivo  Número de Ocorrência, para melhor localizar o estado da sua denúncia nas pendencias policiais.";

    private static final String ACTOR_OCCURRENCE_REFUSED = "Prezado(a), queira encontrar no seu ambiente de registo a ocorrência Número de Ocorrência: %s, devolvida para correção.";

    public SendSMSDTO getSmsContentToOccurrenceReport(String recipient, String occurrenceNumber) {
        SendSMSDTO sendSmsDTO = this.getDefaultSmsContent();
        String message = String.format(SMS_REPORTER_NOTIFICATION_TEMPLATE, occurrenceNumber);
        sendSmsDTO.setMessage(message);
        sendSmsDTO.setNumber(recipient);
        return sendSmsDTO;
    }

    public SendSMSDTO getActorOccurrenceRegistrationNotification(String recipient, String occurrenceNumber) {
        SendSMSDTO sendSmsDTO = this.getDefaultSmsContent();
        String message = String.format(ACTOR_OCCURRANCE_REGISTRATION_EMAIL_TEMPLATE, occurrenceNumber);
        sendSmsDTO.setMessage(message);
        sendSmsDTO.setNumber(recipient);
        return sendSmsDTO;
    }

    public SendSMSDTO getActorOccurrenceRefusedNotification(String recipient, String occurrenceNumber) {
        SendSMSDTO sendSmsDTO = this.getDefaultSmsContent();
        String message = String.format(ACTOR_OCCURRENCE_REFUSED, occurrenceNumber);
        sendSmsDTO.setMessage(message);
        sendSmsDTO.setNumber(recipient);
        return sendSmsDTO;
    }

    private SendSMSDTO getDefaultSmsContent(){

        SendSMSDTO sendSMSDTO = new SendSMSDTO();
        sendSMSDTO.setFrom(defaultSource);
        sendSMSDTO.setOrigin(defaultOrigin);
        return sendSMSDTO;
    }
}

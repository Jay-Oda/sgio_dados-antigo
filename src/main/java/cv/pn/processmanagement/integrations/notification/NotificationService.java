package cv.pn.processmanagement.integrations.notification;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final RabbitTemplate template;
    private final ObjectMapper objectMapper;
    private final String EXCHANGE_NOTIFICATION = "x.notification.work";
    private final String EMAIL_ROUTING_KEY = "email";
    private final String SMS_ROUTING_KEY = "sms";
    private final Logger LOG = LoggerFactory.getLogger(NotificationService.class);


    public NotificationService(RabbitTemplate template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }

    public void sendEmail(SendEmailDTO sendEmailDTO) {
        try {
            String convertedEmailContent = objectMapper.writeValueAsString(sendEmailDTO);
            template.convertAndSend(EXCHANGE_NOTIFICATION, EMAIL_ROUTING_KEY, convertedEmailContent);
            LOG.info("[NotificationService] - Email sent to: {}", sendEmailDTO.getMyto());
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void sendSms(SendSMSDTO sendSMSDTO) {
        try {
            String convertedEmailContent = objectMapper.writeValueAsString(sendSMSDTO);
            template.convertAndSend(EXCHANGE_NOTIFICATION, SMS_ROUTING_KEY, convertedEmailContent);
            LOG.info("[NotificationService] - Sms sent to: {}", sendSMSDTO.getNumber());
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }
}

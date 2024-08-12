package team_alcoholic.jumo_server.global.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAIConfig {
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("유저가 주류에 대한 정보를 주면 이에 대한 테이스팅 노트를 한국어 단어들로 줘야해. 이때 각각의 노트들은 7개를 줘야해")
                .build();
    }

}

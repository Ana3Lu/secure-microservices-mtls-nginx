package co.edu.unisabana.secureclient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.ClientAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() throws Exception {
        // Cargar el keystore del cliente
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream keyStoreFis = new FileInputStream("C:/Users/analu/Downloads/ProyectoFinalPatrones/Certs/client-keystore.p12")) {
            keyStore.load(keyStoreFis, "Client.23".toCharArray());
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "Client.23".toCharArray());

        // Cargar el truststore (con el certificado del servidor)
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream trustStoreIs = getClass().getClassLoader().getResourceAsStream("client-truststore.jks")) {
            if (trustStoreIs == null) {
                throw new RuntimeException("No se encontró el truststore en classpath: client-truststore.jks");
            }
            trustStore.load(trustStoreIs, "changeit".toCharArray());
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        // Crear el contexto SSL de Netty
        SslContext sslContext = SslContextBuilder
                .forClient()
                .keyManager(kmf)
                .trustManager(tmf)
                .build();

        HttpClient httpClient = HttpClient.create()
                .secure(spec -> spec.sslContext(sslContext));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}

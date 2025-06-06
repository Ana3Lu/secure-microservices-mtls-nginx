package co.edu.unisabana.secureclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cliente")
public class SecureClient {

    private final WebClient webClient;

    public SecureClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/obtener")
    public Mono<String> obtenerDatos() {
        // URL del servicio seguro con mTLS
        String url = "https://server:8443/api/data";

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(resp -> "Respuesta del servidor: " + resp)
                .onErrorResume(e -> Mono.just("Error al llamar servidor: " + e.getMessage()));
    }
}

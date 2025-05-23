package co.edu.unisabana.secureapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {

    @GetMapping("/data")
    public ResponseEntity<Map<String, String>> getSecureData() {
        Map<String, String> data = new HashMap<>();
        data.put("status", "ok");
        data.put("service", "inventory");
        data.put("message", "Accessed via mTLS");
        return ResponseEntity.ok(data);
    }
}

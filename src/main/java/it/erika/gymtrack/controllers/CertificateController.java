package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.CertificateDto;
import it.erika.gymtrack.services.CertificateService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("customer/{id}/certificate")  // perchè il certificato non può esistere senza l'utente
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CertificateDto uploadCertificate(@PathVariable(name = "id") UUID id, @RequestParam(name = "expiryDate") Instant expiryDate, @RequestPart(name = "file") MultipartFile file) throws IOException {
        return service.upload(id, file, expiryDate);
    }

    @GetMapping  // non serve specificare l'id perchè l'ho inserita nel request mapping
    public CertificateDto getById(@PathVariable(name = "id") UUID id) {
        return service.getById(id);
    }

    @RequestMapping(method = RequestMethod.HEAD)   // Metodo http che ci fornisce dei metadati riguardanti una risorsa
    public boolean existValidCertificate(@PathVariable(name = "id") UUID id) {
        return service.existValidCertificate(id);
    }

    @DeleteMapping
    public void deleteCertificate(@PathVariable(name = "id") UUID id) {
        service.delete(id);
    }

}

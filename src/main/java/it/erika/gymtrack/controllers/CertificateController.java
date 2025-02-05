package it.erika.gymtrack.controllers;

import it.erika.gymtrack.dto.CertificateDto;
import it.erika.gymtrack.services.CertificateService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("certificate")
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CertificateDto uploadCertificate(@RequestParam(name = "customerId") UUID customerId, @RequestPart(name = "file") MultipartFile file) {
        return service.upload(file, customerId);
    }

    @GetMapping("{id}")
    public CertificateDto getById(@PathVariable(name = "id") UUID id) {
        return service.getById(id);
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "{id}")   // Metodo http che ci fornisce dei metadati riguardanti una risorsa
    public boolean existValidCertificate(@PathVariable(name = "customerId") UUID customerId) {
        return service.existValidCertificate(customerId);
    }

    @DeleteMapping("{id}")
    public void deleteCertificate(@PathVariable(name = "id") UUID id) {
        service.delete(id);
    }

}

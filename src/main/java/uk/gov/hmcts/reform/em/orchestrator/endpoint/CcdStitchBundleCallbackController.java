package uk.gov.hmcts.reform.em.orchestrator.endpoint;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import uk.gov.hmcts.reform.em.orchestrator.service.ccdcallbackhandler.CcdCallbackDto;
import uk.gov.hmcts.reform.em.orchestrator.service.ccdcallbackhandler.CcdCallbackDtoCreator;
import uk.gov.hmcts.reform.em.orchestrator.service.ccdcallbackhandler.CcdCallbackHandlerService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
public class CcdStitchBundleCallbackController {

    private CcdCallbackHandlerService ccdCallbackHandlerService;
    private CcdCallbackDtoCreator ccdCallbackDtoCreator;

    public CcdStitchBundleCallbackController(CcdCallbackHandlerService ccdCallbackHandlerService, CcdCallbackDtoCreator ccdCallbackDtoCreator) {
        this.ccdCallbackHandlerService = ccdCallbackHandlerService;
        this.ccdCallbackDtoCreator = ccdCallbackDtoCreator;
    }

    @PostMapping(value = "/api/stitch-cdd-bundles",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> stitchCcdBundles(HttpServletRequest request) throws IOException {
        CcdCallbackDto ccdCallbackDto = ccdCallbackDtoCreator.createDto(request, "caseBundles");
        return ResponseEntity.ok(ccdCallbackHandlerService.handleCddCallback(ccdCallbackDto));
    }

}

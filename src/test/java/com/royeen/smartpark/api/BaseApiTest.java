package com.royeen.smartpark.api;

import com.royeen.smartpark.models.presentation.base.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class BaseApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation).snippets().withTemplateFormat(TemplateFormats.markdown()))
                .alwaysDo(document("{class-name}/{method-name}-{step}",preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }


    protected ApiResponse getApiResponseFromString(String contentAsString) {
        return objectMapper.readValue(contentAsString, ApiResponse.class);
    }

    protected  <T> T getObjectDataFromApiResponse(ApiResponse response, Class<T> clazz) {
        return objectMapper.readValue(objectMapper.writeValueAsString(response.data()), clazz);
    }

    protected <T> List<T> getListDataFromApiResponse(ApiResponse response, Class<T> clazz) {
        return objectMapper.readValue(objectMapper.writeValueAsString(response.data()), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}

package io.orep.contract;

import io.orep.exception.Error;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jboss.resteasy.spi.HttpResponseCodes;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestHTTPEndpoint(ContractResource.class)
public class ContractResourceTest {

    @Test
    public void get() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void getById() {
        Contract contract = this.post();
        given()
                .when()
                .get("/{contractId}", contract.getContractId())
                .then()
                .statusCode(200);
    }

    public Contract post() {
        Contract contract = this.mockContract();
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(contract)
                .post();
        assertThat(response.getHeader(HttpHeaders.LOCATION)).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpResponseCodes.SC_CREATED);
        Contract saved = response.getBody().as(Contract.class);
        assertThat(saved.getContractId()).isNotNull();
        assertThat(saved.getStartDate()).isEqualTo(contract.getStartDate());
        assertThat(saved.getEndDate()).isEqualTo(contract.getEndDate());
        return saved;
    }

    @Test
    public void postFailDateValidator() {
        Contract contract = this.mockContract();
        contract.setEndDate(contract.getStartDate().minusDays(1));
        Response response = given()
                .contentType(ContentType.JSON)
                .body(contract)
                .post();
        assertThat(response.getStatusCode()).isEqualTo(HttpResponseCodes.SC_BAD_REQUEST);
        List<Error> errors = Arrays.stream(response.getBody().as(Error[].class)).toList();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).extracting("path").isEqualTo("post.contract");
    }

    @Test
    public void put() {
        Contract saved = this.post();
        saved.setExecutionDate(LocalDate.now());
        Contract updated = given()
                .contentType(ContentType.JSON)
                .body(saved)
                .put("/{contractId}", saved.getContractId())
                .then()
                .statusCode(200)
                .extract().as(Contract.class);
        assertThat(updated.getExecutionDate()).isEqualTo(LocalDate.now());
    }

    private Contract mockContract() {
        Contract contract = new Contract();
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(1));
        return contract;
    }

}
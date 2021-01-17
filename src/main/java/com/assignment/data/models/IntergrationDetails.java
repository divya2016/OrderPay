package com.assignment.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity to track third party API calls
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("integrationDetails")
public class IntergrationDetails extends AuditEntity {
    private static final long serialVersionUID = 1L;
    private Object apiRequest;
    private Object apiResponse;
    private String status;
    private String apiUrl;
    private long timeTaken;

}

package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.models.Severity;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Role(Role.Type.EVENT)
@Timestamp("executionTime")
public class Alert {
    private UUID id = UUID.randomUUID();
    @NonNull
    private String code;
    @NonNull
    private Severity severity;
    @NonNull
    private String description;

    private Date executionTime = new Date();
}

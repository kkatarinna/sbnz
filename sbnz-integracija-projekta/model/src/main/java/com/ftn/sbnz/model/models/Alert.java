package com.ftn.sbnz.model.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Alert {
    private UUID id = UUID.randomUUID();
    @NonNull
    private String code;
    @NonNull
    private Severity severity;
    @NonNull
    private String description;
}

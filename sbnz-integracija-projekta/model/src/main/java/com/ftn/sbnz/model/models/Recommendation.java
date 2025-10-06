package com.ftn.sbnz.model.models;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Recommendation {
    private UUID id = UUID.randomUUID();
    @NonNull
    private String action;
    @NonNull
    private String rationale;
}

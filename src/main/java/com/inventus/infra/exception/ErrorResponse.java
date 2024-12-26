package com.inventus.infra.exception;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String mensagem, LocalDateTime data) {
}
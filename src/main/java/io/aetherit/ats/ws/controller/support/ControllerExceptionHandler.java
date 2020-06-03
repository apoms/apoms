package io.aetherit.ats.ws.controller.support;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.aetherit.ats.ws.exception.AtsWsException;
import io.aetherit.ats.ws.exception.BadRequestException;
import io.aetherit.ats.ws.exception.CanNotFoundExchangeStatusException;
import io.aetherit.ats.ws.exception.CanNotFoundTrxException;
import io.aetherit.ats.ws.exception.CanNotFoundTrxHashException;
import io.aetherit.ats.ws.exception.CanNotFoundTypeException;
import io.aetherit.ats.ws.exception.CanNotFoundUserTypeException;
import io.aetherit.ats.ws.exception.CanNotFoundWalletException;
import io.aetherit.ats.ws.exception.ErrorCode;
import io.aetherit.ats.ws.exception.ErrorResponse;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest httpRequest, BadRequestException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(CanNotFoundTrxException.class)
    public ResponseEntity<ErrorResponse> handleCanNotFoundTrxException(HttpServletRequest httpRequest, CanNotFoundTrxException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(CanNotFoundTypeException.class)
    public ResponseEntity<ErrorResponse> handleCanNotFoundTypeException(HttpServletRequest httpRequest, CanNotFoundTypeException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(CanNotFoundExchangeStatusException.class)
    public ResponseEntity<ErrorResponse> handleCanNotFoundExchangeStatusException(HttpServletRequest httpRequest, CanNotFoundExchangeStatusException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(CanNotFoundTrxHashException.class)
    public ResponseEntity<ErrorResponse> handleCanNotFoundTrxHashException(HttpServletRequest httpRequest, CanNotFoundTrxHashException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(CanNotFoundWalletException.class)
    public ResponseEntity<ErrorResponse> handleCanNotFoundWalletException(HttpServletRequest httpRequest, CanNotFoundWalletException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }
    
    @ExceptionHandler(CanNotFoundUserTypeException.class)
    public ResponseEntity<ErrorResponse> handleCanNotFoundUserTypeException(HttpServletRequest httpRequest, CanNotFoundUserTypeException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(AtsWsException.class)
    public ResponseEntity<ErrorResponse> handleUpayPsException(HttpServletRequest httpRequest, AtsWsException ex) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpServletRequest httpServletRequest = null;
        if(request instanceof ServletWebRequest) {
            httpServletRequest = ((ServletWebRequest) request).getNativeRequest(HttpServletRequest.class);
        }
        final ErrorResponse response = getErrorResponse(ex, httpServletRequest, status);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(HttpServletRequest httpRequest, Exception ex) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorResponse response = getErrorResponse(ex, httpRequest, status);

        return new ResponseEntity<>(response, status);
    }

    private ErrorResponse getErrorResponse(Exception ex, HttpServletRequest httpServletRequest, HttpStatus status) {
        ErrorCode errorCode = ErrorCode.Unknown;
        if(ex instanceof AtsWsException) {
            errorCode = ((AtsWsException) ex).getErrorCode();
        }

        String requestPath = "";
        if(httpServletRequest != null) {
            requestPath = httpServletRequest.getRequestURI();

            if (httpServletRequest.getQueryString() != null) {
                requestPath += "?" + httpServletRequest.getQueryString();
            }
        }
        String remoteAddress = "";
        if(httpServletRequest != null) {
            remoteAddress = httpServletRequest.getRemoteAddr();
        }

        final ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(errorCode)
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .path(requestPath)
                .build();

        logger.info("HTTP Response [{} {}] to {} : {}", status.value(), status.name(), remoteAddress, response);

        return response;
    }
}

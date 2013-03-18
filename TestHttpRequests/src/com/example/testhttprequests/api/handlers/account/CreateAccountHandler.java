package com.example.testhttprequests.api.handlers.account;

import java.util.EnumSet;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import com.example.testhttprequests.api.handlers.HootcasterApiErrorHandler;
import com.example.testhttprequests.api.handlers.HootcasterApiHandler;
import com.example.testhttprequests.api.handlers.HootcasterErrorResponse;
import com.example.testhttprequests.api.handlers.HootcasterResponse;
import com.example.testhttprequests.api.handlers.account.CreateAccountHandler.CreateAccountError;

public abstract class CreateAccountHandler implements HootcasterApiHandler, HootcasterApiErrorHandler<CreateAccountError> {
	public abstract void handleSuccess();

	public static enum CreateAccountError {
		USERNAME_INVALID("username_invalid"),
		PASSWORD_INVALID("password_invalid"),
		EMAIL_INVALID("email_invalid"),
		REGISTRATION_ID_INVALID("registration_id_invalid"),
		
		USERNAME_EXISTS("username_exists"),
		EMAIL_EXISTS("email_exists"),
		PHONE_EXISTS("phone_exists"),
		REGISTRATION_ID_EXISTS("registration_id_exists"),
		
		UNKNOWN("other");

		private final String value;
		private CreateAccountError(String value) { this.value = value; }
		
		@JsonCreator
		public static CreateAccountError fromValue(String value) {
			for (CreateAccountError error : CreateAccountError.values()) {
				if (error.value.equals(value))
					return error;
			}
			return UNKNOWN;
		}
	}
	
	public static final class CreateAccountResponse extends HootcasterErrorResponse<Void, CreateAccountError> {
		@JsonCreator
		protected CreateAccountResponse(
				@JsonProperty("okay") boolean okay,
				@JsonProperty("data") Void data,
				@JsonProperty("errors") EnumSet<CreateAccountError> errors) {
			super(okay, data, errors);
		}
		
		@SuppressWarnings("deprecation")
		public static JavaType getResponseType() {
			return TypeFactory.parametricType(HootcasterResponse.class, Void.class, CreateAccountError.class);
		}
	}
}
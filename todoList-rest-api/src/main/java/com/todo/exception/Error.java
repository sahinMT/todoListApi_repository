package com.todo.exception;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.todo.model.entity.HttpDetailStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Validated
public class Error {
	@JsonProperty("code")
	private String code = null;

	@JsonProperty("reason")
	private String reason = null;

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("status")
	private String status = null;

	public Error code(String code) {
		this.code = code;
		return this;
	}

	@JsonProperty("httpStatus")
	private HttpStatus httpStatus = null;

	@JsonProperty("httpDetailStatus")
	private HttpDetailStatus httpDetailStatus = null;

	/**
	 * Application relevant detail, defined in the API or a common list.
	 * 
	 * @return code
	 **/
	@Schema(required = true, description = "Application relevant detail, defined in the API or a common list.")
	@NotNull

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Error reason(String reason) {
		this.reason = reason;
		return this;
	}

	/**
	 * Explanation of the reason for the error which can be shown to a client user.
	 * 
	 * @return reason
	 **/
	@Schema(required = true, description = "Explanation of the reason for the error which can be shown to a client user.")
	@NotNull

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Error message(String message) {
		this.message = message;
		return this;
	}

	/**
	 * More details and corrective actions related to the error which can be shown
	 * to a client user.
	 * 
	 * @return message
	 **/
	@Schema(description = "More details and corrective actions related to the error which can be shown to a client user.")

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Error status(String status) {
		this.status = status;
		return this;
	}

	/**
	 * HTTP Error code extension
	 * 
	 * @return status
	 **/
	@Schema(description = "HTTP Error code extension")

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Error error = (Error) o;
		return Objects.equals(this.code, error.code) && Objects.equals(this.reason, error.reason)
				&& Objects.equals(this.message, error.message) && Objects.equals(this.status, error.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, reason, message, status);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Error {\n");

		sb.append("    code: ").append(toIndentedString(code)).append("\n");
		sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
		sb.append("    message: ").append(toIndentedString(message)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public HttpDetailStatus getHttpDetailStatus() {
		return httpDetailStatus;
	}

	public void setHttpDetailStatus(HttpDetailStatus httpDetailStatus) {
		this.httpDetailStatus = httpDetailStatus;
	}

}

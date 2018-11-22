package database;

public class Credentials {
	private String ip;
	private String status;
	private String errorMessage;
	private String securityName;
	private String securityLevel;
	private String authType;
	private String privType;
	private String authPassphrase;
	private String privPassphrase;
	private String community;
	private String port;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIp() {
		return ip;
	}

	public String getStatus() {
		return status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getSecurityName() {
		return securityName;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public String getAuthType() {
		return authType;
	}

	public String getPrivType() {
		return privType;
	}

	public String getAuthPassphrase() {
		return authPassphrase;
	}

	public String getPrivPassphrase() {
		return privPassphrase;
	}

	public String getCommunity() {
		return community;
	}

	public String getPort() {
		return port;
	}

	public void setIp(String newIp) {
		ip = newIp;
	}

	public void setStatus(String newStatus) {
		status = newStatus;
	}

	public void setErrorMessage(String newErrorMessage) {
		errorMessage = newErrorMessage;
	}

	public void setSecurityName(String newSecurityName) {
		securityName = newSecurityName;
	}

	public void setSecurityLevel(String newSecurityLevel) {
		securityLevel = newSecurityLevel;
	}

	public void setAuthType(String newAuthType) {
		authType = newAuthType;
	}

	public void setPrivType(String newPrivType) {
		privType = newPrivType;
	}

	public void setAuthPassphrase(String newAuthPassphrase) {
		authPassphrase = newAuthPassphrase;
	}

	public void setPrivPassphrase(String newPrivPassphrase)

	{
		privPassphrase = newPrivPassphrase;
	}

	public void setCommunity(String newCommunity) {
		community = newCommunity;
	}

	public void setPort(String newPort) {
		port = newPort;
	}
}
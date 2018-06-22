package vgl.platform;

public class AppConfig {

	public class Builder {

		private boolean	runInternalLoopsFirst;
		private boolean	hasProcessManager;
		private int		fixedUpdateTimestamp;
		private byte	gl_major;
		private byte	gl_minor;

		public Builder hasProcessManager(boolean has) {
			this.hasProcessManager = has;
			return this;
		}

		public Builder runInternalLoopsFirst(boolean internalsFirst) {
			this.runInternalLoopsFirst = internalsFirst;
			return this;
		}

		public Builder withMajorVersion(byte ver) {
			this.gl_major = ver;
			return this;
		}

		public Builder withMinorVersion(byte ver) {
			return this;
		}
	}

	private boolean	runInternalLoopsFirst;
	private boolean	hasProcessManager;
	private int		fixedUpdateTimestamp;
	private byte	gl_major;
	private byte	gl_minor;

	private AppConfig(AppConfig.Builder builder) {

	}

}

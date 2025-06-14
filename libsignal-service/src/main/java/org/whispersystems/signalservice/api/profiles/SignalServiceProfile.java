package org.whispersystems.signalservice.api.profiles;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.signal.libsignal.protocol.logging.Log;
import org.signal.libsignal.zkgroup.InvalidInputException;
import org.signal.libsignal.zkgroup.profiles.ExpiringProfileKeyCredentialResponse;
import org.whispersystems.signalservice.api.push.ServiceId;
import org.whispersystems.signalservice.internal.util.JsonUtil;

import java.math.BigDecimal;
import java.util.List;

public class SignalServiceProfile {

  public enum RequestType {
    PROFILE,
    PROFILE_AND_CREDENTIAL
  }

  private static final String TAG = SignalServiceProfile.class.getSimpleName();

  @JsonProperty
  private String identityKey;

  @JsonProperty
  private String name;

  @JsonProperty
  private String about;

  @JsonProperty
  private String aboutEmoji;

  @JsonProperty
  private byte[] paymentAddress;

  @JsonProperty
  private String avatar;

  @JsonProperty
  private String unidentifiedAccess;

  @JsonProperty
  private boolean unrestrictedUnidentifiedAccess;

  @JsonProperty
  private Capabilities capabilities;

  @JsonProperty
  @JsonSerialize(using = JsonUtil.ServiceIdSerializer.class)
  @JsonDeserialize(using = JsonUtil.ServiceIdDeserializer.class)
  private ServiceId uuid;

  @JsonProperty
  private byte[] credential;

  @JsonProperty
  private List<Badge> badges;

  @JsonProperty
  private String phoneNumberSharing;

  @JsonIgnore
  private RequestType requestType;

  public SignalServiceProfile() {}

  public String getIdentityKey() {
    return identityKey;
  }

  public String getName() {
    return name;
  }

  public String getAbout() {
    return about;
  }

  public String getAboutEmoji() {
    return aboutEmoji;
  }

  public byte[] getPaymentAddress() {
    return paymentAddress;
  }

  public String getAvatar() {
    return avatar;
  }

  public String getUnidentifiedAccess() {
    return unidentifiedAccess;
  }

  public String getPhoneNumberSharing() {
    return phoneNumberSharing;
  }

  public boolean isUnrestrictedUnidentifiedAccess() {
    return unrestrictedUnidentifiedAccess;
  }

  public Capabilities getCapabilities() {
    return capabilities;
  }

  public List<Badge> getBadges() {
    return badges;
  }

  public ServiceId getServiceId() {
    return uuid;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestType requestType) {
    this.requestType = requestType;
  }

  public static class Badge {
    @JsonProperty
    private String id;

    @JsonProperty
    private String category;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private List<String> sprites6;

    @JsonProperty
    private BigDecimal expiration;

    @JsonProperty
    private boolean visible;

    @JsonProperty
    private long duration;

    public String getId() {
      return id;
    }

    public String getCategory() {
      return category;
    }

    public String getName() {
      return name;
    }

    public String getDescription() {
      return description;
    }

    public List<String> getSprites6() {
      return sprites6;
    }

    public BigDecimal getExpiration() {
      return expiration;
    }

    public boolean isVisible() {
      return visible;
    }

    /**
     * @return Duration badge is valid for, in seconds.
     */
    public long getDuration() {
      return duration;
    }
  }

  public static class Capabilities {
    @JsonProperty
    private boolean storage;

    @JsonProperty("ssre2")
    private boolean storageServiceEncryptionV2;

    @JsonProperty
    private boolean extralock;

    @JsonCreator
    public Capabilities() {}

    public Capabilities(boolean storage, boolean storageServiceEncryptionV2, boolean extralock) {
      this.storage                    = storage;
      this.storageServiceEncryptionV2 = storageServiceEncryptionV2;
      this.extralock                  = extralock;
    }

    public boolean isStorage() {
      return storage;
    }

    public boolean isStorageServiceEncryptionV2() {
      return storageServiceEncryptionV2;
    }

    public boolean isExtralock() {
      return extralock;
    }
  }

  public ExpiringProfileKeyCredentialResponse getExpiringProfileKeyCredentialResponse() {
    if (credential == null) return null;

    try {
      return new ExpiringProfileKeyCredentialResponse(credential);
    } catch (InvalidInputException e) {
      Log.w(TAG, e);
      return null;
    }
  }
}

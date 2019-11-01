package com.lsolier.udacity.vehiclesapi.config.auditing;


import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.UUID;

public class CustomUser extends User implements Serializable {

  private static final long serialVersionUID = 1L;
  private UUID uuid;

  public CustomUser(User user) {
    super(user.getUsername(), user.getPassword(), user.getAuthorities());
  }

  public UUID getId() {
    return uuid;
  }
  public void setId() {
    this.uuid = uuid;
  }

}

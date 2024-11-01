package org.interactor.security;

import java.util.List;

public class UrlSecurityConfiguration {
    String url;
    Action assignedActionsMappingToUrls;

    List<Role> rolesThatPermitsAccess;

    public UrlSecurityConfiguration(String url, Action assignedActionsMappingToUrls, List<Role> rolesThatPermitsAccess) {
        this.url = url;
        this.assignedActionsMappingToUrls = assignedActionsMappingToUrls;
        this.rolesThatPermitsAccess = rolesThatPermitsAccess;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Action getAssignedActionsMappingToUrls() {
        return assignedActionsMappingToUrls;
    }

    public void setAssignedActionsMappingToUrls(Action assignedActionsMappingToUrls) {
        this.assignedActionsMappingToUrls = assignedActionsMappingToUrls;
    }

    public List<Role> getRolesThatPermitsAccess() {
        return rolesThatPermitsAccess;
    }

    public void setRolesThatPermitsAccess(List<Role> rolesThatPermitsAccess) {
        this.rolesThatPermitsAccess = rolesThatPermitsAccess;
    }
}

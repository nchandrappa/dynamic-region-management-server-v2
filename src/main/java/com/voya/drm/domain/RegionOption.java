package com.voya.drm.domain;

import java.util.Arrays;

import com.gemstone.gemfire.cache.RegionShortcut;

public class RegionOption {

	private RegionShortcut type = RegionShortcut.PARTITION;
	private String templateRegion = null;
	private String[] groups = null;
	private Boolean skipIfExists = false;
	private String keyConstraint = null;
	private String valueConstraint = null;
	private Boolean enableStatistics = null;
	private Integer entryIdleTimeExpiration = null;
	private String entryIdleTimeExpirationAction = null;
	private Integer entryTimeToLiveExpiration = null;
	private String entryTimeToLiveExpirationAction = null;
	private Integer regionIdleTimeExpiration = null;
	private String regionIdleTimeExpirationAction = null;
	private Integer regionTimeToLiveExpiration = null;
	private String regionTimeToLiveExpirationAction = null;
	private String diskStore = null;
	private Boolean enableSynchronousDisk = null;
	private Boolean enableAsyncConflation = null;
	private Boolean enableSubscriptionConflation = null;
	private String[] cacheListeners = null;
	private String cacheLoader = null;
	private String cacheWriter = null;
	private String[] asyncEventQueueIds = null;
	private String[] gatewaySenderIds = null;
	private Boolean enableConcurrencyCheck = null;
	private Boolean enableCloning = null;
	private Integer concurrencyLevel = null;
	private String colocatedWith = null;
	private Integer localMaxMemory = null;
	private Long recoveryDelay = null;
	private Integer redundantCopies = null;
	private Long startupRecoveryDelay = null;
	private Long totalMaxMemory = null;
	private Integer totalNumBuckets = null;
	private String compressor = null;
	private Integer evictionMax = null;



	public RegionShortcut getType() {
		return type;
	}



	public void setType(String typeString) {
		this.type = RegionShortcut.valueOf(typeString);
		if (this.type == null) {
			this.type = RegionShortcut.PARTITION;
		}
	}

	public String getTemplateRegion() {
		return templateRegion;
	}



	public void setTemplateRegion(String templateRegion) {
		this.templateRegion = templateRegion;
	}



	public String[] getGroups() {
		return groups;
	}



	public void setGroups(String[] groups) {
		this.groups = groups;
	}



	public Boolean getSkipIfExists() {
		return skipIfExists;
	}



	public void setSkipIfExists(Boolean skipIfExists) {
		this.skipIfExists = skipIfExists;
	}



	public String getKeyConstraint() {
		return keyConstraint;
	}



	public void setKeyConstraint(String keyConstraint) {
		this.keyConstraint = keyConstraint;
	}



	public String getValueConstraint() {
		return valueConstraint;
	}



	public void setValueConstraint(String valueConstraint) {
		this.valueConstraint = valueConstraint;
	}



	public Boolean getEnableStatistics() {
		return enableStatistics;
	}



	public void setEnableStatistics(Boolean enableStatistics) {
		this.enableStatistics = enableStatistics;
	}



	public Integer getEntryIdleTimeExpiration() {
		return entryIdleTimeExpiration;
	}



	public void setEntryIdleTimeExpiration(Integer entryIdleTimeExpiration) {
		this.entryIdleTimeExpiration = entryIdleTimeExpiration;
	}



	public String getEntryIdleTimeExpirationAction() {
		return entryIdleTimeExpirationAction;
	}



	public void setEntryIdleTimeExpirationAction(
			String entryIdleTimeExpirationAction) {
		this.entryIdleTimeExpirationAction = entryIdleTimeExpirationAction;
	}



	public Integer getEntryTimeToLiveExpiration() {
		return entryTimeToLiveExpiration;
	}



	public void setEntryTimeToLiveExpiration(Integer entryTimeToLiveExpiration) {
		this.entryTimeToLiveExpiration = entryTimeToLiveExpiration;
	}



	public String getEntryTimeToLiveExpirationAction() {
		return entryTimeToLiveExpirationAction;
	}



	public void setEntryTimeToLiveExpirationAction(
			String entryTimeToLiveExpirationAction) {
		this.entryTimeToLiveExpirationAction = entryTimeToLiveExpirationAction;
	}



	public Integer getRegionIdleTimeExpiration() {
		return regionIdleTimeExpiration;
	}



	public void setRegionIdleTimeExpiration(Integer regionIdleTimeExpiration) {
		this.regionIdleTimeExpiration = regionIdleTimeExpiration;
	}



	public String getRegionIdleTimeExpirationAction() {
		return regionIdleTimeExpirationAction;
	}



	public void setRegionIdleTimeExpirationAction(
			String regionIdleTimeExpirationAction) {
		this.regionIdleTimeExpirationAction = regionIdleTimeExpirationAction;
	}



	public Integer getRegionTimeToLiveExpiration() {
		return regionTimeToLiveExpiration;
	}



	public void setRegionTimeToLiveExpiration(Integer regionTimeToLiveExpiration) {
		this.regionTimeToLiveExpiration = regionTimeToLiveExpiration;
	}



	public String getRegionTimeToLiveExpirationAction() {
		return regionTimeToLiveExpirationAction;
	}



	public void setRegionTimeToLiveExpirationAction(
			String regionTimeToLiveExpirationAction) {
		this.regionTimeToLiveExpirationAction = regionTimeToLiveExpirationAction;
	}



	public String getDiskStore() {
		return diskStore;
	}



	public void setDiskStore(String diskStore) {
		this.diskStore = diskStore;
	}



	public Boolean getEnableSynchronousDisk() {
		return enableSynchronousDisk;
	}



	public void setEnableSynchronousDisk(Boolean enableSynchronousDisk) {
		this.enableSynchronousDisk = enableSynchronousDisk;
	}



	public Boolean getEnableAsyncConflation() {
		return enableAsyncConflation;
	}



	public void setEnableAsyncConflation(Boolean enableAsyncConflation) {
		this.enableAsyncConflation = enableAsyncConflation;
	}



	public Boolean getEnableSubscriptionConflation() {
		return enableSubscriptionConflation;
	}



	public void setEnableSubscriptionConflation(Boolean enableSubscriptionConflation) {
		this.enableSubscriptionConflation = enableSubscriptionConflation;
	}



	public String[] getCacheListeners() {
		return cacheListeners;
	}



	public void setCacheListeners(String[] cacheListeners) {
		this.cacheListeners = cacheListeners;
	}



	public String getCacheLoader() {
		return cacheLoader;
	}



	public void setCacheLoader(String cacheLoader) {
		this.cacheLoader = cacheLoader;
	}



	public String getCacheWriter() {
		return cacheWriter;
	}



	public void setCacheWriter(String cacheWriter) {
		this.cacheWriter = cacheWriter;
	}



	public String[] getAsyncEventQueueIds() {
		return asyncEventQueueIds;
	}



	public void setAsyncEventQueueIds(String[] asyncEventQueueIds) {
		this.asyncEventQueueIds = asyncEventQueueIds;
	}



	public String[] getGatewaySenderIds() {
		return gatewaySenderIds;
	}



	public void setGatewaySenderIds(String[] gatewaySenderIds) {
		this.gatewaySenderIds = gatewaySenderIds;
	}



	public Boolean getEnableConcurrencyCheck() {
		return enableConcurrencyCheck;
	}



	public void setEnableConcurrencyCheck(Boolean enableConcurrencyCheck) {
		this.enableConcurrencyCheck = enableConcurrencyCheck;
	}



	public Boolean getEnableCloning() {
		return enableCloning;
	}



	public void setEnableCloning(Boolean enableCloning) {
		this.enableCloning = enableCloning;
	}



	public Integer getConcurrencyLevel() {
		return concurrencyLevel;
	}



	public void setConcurrencyLevel(Integer concurrencyLevel) {
		this.concurrencyLevel = concurrencyLevel;
	}



	public String getColocatedWith() {
		return colocatedWith;
	}



	public void setColocatedWith(String colocatedWith) {
		this.colocatedWith = colocatedWith;
	}



	public Integer getLocalMaxMemory() {
		return localMaxMemory;
	}



	public void setLocalMaxMemory(Integer localMaxMemory) {
		this.localMaxMemory = localMaxMemory;
	}



	public Long getRecoveryDelay() {
		return recoveryDelay;
	}



	public void setRecoveryDelay(Long recoveryDelay) {
		this.recoveryDelay = recoveryDelay;
	}



	public Integer getRedundantCopies() {
		return redundantCopies;
	}



	public void setRedundantCopies(Integer redundantCopies) {
		this.redundantCopies = redundantCopies;
	}



	public Long getStartupRecoveryDelay() {
		return startupRecoveryDelay;
	}



	public void setStartupRecoveryDelay(Long startupRecoveryDelay) {
		this.startupRecoveryDelay = startupRecoveryDelay;
	}



	public Long getTotalMaxMemory() {
		return totalMaxMemory;
	}



	public void setTotalMaxMemory(Long totalMaxMemory) {
		this.totalMaxMemory = totalMaxMemory;
	}



	public Integer getTotalNumBuckets() {
		return totalNumBuckets;
	}



	public void setTotalNumBuckets(Integer totalNumBuckets) {
		this.totalNumBuckets = totalNumBuckets;
	}



	public String getCompressor() {
		return compressor;
	}



	public void setCompressor(String compressor) {
		this.compressor = compressor;
	}



	public Integer getEvictionMax() {
		return evictionMax;
	}



	public void setEvictionMax(Integer evictionMax) {
		this.evictionMax = evictionMax;
	}



	@Override
	public String toString() {
		return "RegionOptionsBean [templateRegion=" + templateRegion
				+ ", group=" + Arrays.toString(groups) + ", skipIfExists="
				+ skipIfExists + ", keyConstraint=" + keyConstraint
				+ ", valueConstraint=" + valueConstraint
				+ ", enableStatistics=" + enableStatistics
				+ ", entryIdleTimeExpiration=" + entryIdleTimeExpiration
				+ ", entryIdleTimeExpirationAction="
				+ entryIdleTimeExpirationAction
				+ ", entryTimeToLiveExpiration=" + entryTimeToLiveExpiration
				+ ", entryTimeToLiveExpirationAction="
				+ entryTimeToLiveExpirationAction
				+ ", regionIdleTimeExpiration=" + regionIdleTimeExpiration
				+ ", regionIdleTimeExpirationAction="
				+ regionIdleTimeExpirationAction
				+ ", regionTimeToLiveExpiration=" + regionTimeToLiveExpiration
				+ ", regionTimeToLiveExpirationAction="
				+ regionTimeToLiveExpirationAction + ", diskStore=" + diskStore
				+ ", enableSynchronousDisk=" + enableSynchronousDisk
				+ ", enableAsyncConflation=" + enableAsyncConflation
				+ ", enableSubscriptionConflation="
				+ enableSubscriptionConflation + ", cacheListener="
				+ Arrays.toString(cacheListeners) + ", cacheLoader="
				+ cacheLoader + ", cacheWriter=" + cacheWriter
				+ ", asyncEventQueueIds=" + Arrays.toString(asyncEventQueueIds)
				+ ", gatewaySenderIds=" + Arrays.toString(gatewaySenderIds)
				+ ", enableConcurrencyCheck=" + enableConcurrencyCheck
				+ ", enableCloning=" + enableCloning + ", concurrencyLevel="
				+ concurrencyLevel + ", colocatedWith=" + colocatedWith
				+ ", localMaxMemory=" + localMaxMemory + ", recoveryDelay="
				+ recoveryDelay + ", redundantCopies=" + redundantCopies
				+ ", startupRecoveryDelay=" + startupRecoveryDelay
				+ ", totalMaxMemory=" + totalMaxMemory + ", totalNumBuckets="
				+ totalNumBuckets + ", compressor=" + compressor + "]";
	}

}

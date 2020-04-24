package com.hashedin.redmask.service;

import com.hashedin.redmask.configurations.MaskType;
import com.hashedin.redmask.configurations.MissingParameterException;
import com.hashedin.redmask.configurations.TemplateConfiguration;

import java.util.Map;
import java.util.Set;

/**
 * This is a abstract class which contains parameters and function 
 * needed to implement/construct masking function definition.
 * 
 * All Specific masking function class needs to extend this class.
 */
public abstract class MaskingRuleDef {

  private String columnName; 

  // Value from MaskType enum.
  private MaskType maskType; 

  // A map(key, value) of additional parameters needed for this masking rule.
  private Map<String, String> maskParams;

  public MaskingRuleDef(String columnName, MaskType maskType, Map<String, String> maskParams) {
    this.columnName = columnName;
    this.maskType = maskType;
    this.maskParams = maskParams;
  }

  public MaskingRuleDef() {}

  public abstract void addFunctionDefinition(TemplateConfiguration config, Set<String> funcSet);

  public abstract String getSubQuery(TemplateConfiguration config, String tableName) throws MissingParameterException;

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public MaskType getMaskType() {
    return maskType;
  }

  public void setMaskType(MaskType maskType) {
    this.maskType = maskType;
  }

  public Map<String, String> getMaskParams() {
    return maskParams;
  }

  public void setMaskParams(Map<String, String> maskParams) {
    this.maskParams = maskParams;
  }

}

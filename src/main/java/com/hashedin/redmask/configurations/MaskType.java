package com.hashedin.redmask.configurations;

import com.hashedin.redmask.MaskingFunction.*;
import com.hashedin.redmask.service.MaskingRuleDef;

public enum MaskType {

  STRING_MASKING(StringMasking.class),

  // Masking rule related to email field.
  EMAIL_SHOW_DOMAIN(EmailMasking.class),
  EMAIL_SHOW_FIRST_CHARACTER_DOMAIN(EmailMasking.class),
  EMAIL_SHOW_FIRST_CHARACTERS(EmailMasking.class),
  EMAIL_MASK_ALPHANUMERIC(EmailMasking.class),

  // Masking rule related to Integer and float field.
  RANDOM_INTEGER_FIXED_WIDTH(FixedSizeIntegerMasking.class),
  RANDOM_INTEGER_WITHIN_RANGE(InRangeIntegerMasking.class),
  INTEGER_FIXED_VALUE(FixedValueIntegerMasking.class),
  FLOAT_FIXED_VALUE(FixedValueFloatMasking.class),

  // Masking integer/numeric field within a range.
  NUMERIC_RANGE(NumericRangeMasking.class),
  INTEGER_RANGE(IntegerRangeMasking.class),
  BIGINT_RANGE(BigIntRangeMasking.class),

  // Masking rule related to Credit card field.
  CREDIT_CARD_SHOW_FIRST(CardMasking.class),
  CREDIT_CARD_SHOW_LAST(CardMasking.class),
  CREDIT_CARD_SHOW_FIRST_LAST(CardMasking.class),

  DESTRUCTION;

  Class<? extends MaskingRuleDef> classType;

  MaskType(Class<? extends MaskingRuleDef> classType) {
    this.classType = classType;
  }

  MaskType() {
    this.classType = null;
  }

  public Class<? extends MaskingRuleDef> getClassType() {
    return classType;
  }

  public void setClassType(Class<? extends MaskingRuleDef> classType) {
    this.classType = classType;
  }
}

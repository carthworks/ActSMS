package com.actsms.app.data.parsing;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class SmsParserImpl_Factory implements Factory<SmsParserImpl> {
  @Override
  public SmsParserImpl get() {
    return newInstance();
  }

  public static SmsParserImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SmsParserImpl newInstance() {
    return new SmsParserImpl();
  }

  private static final class InstanceHolder {
    private static final SmsParserImpl_Factory INSTANCE = new SmsParserImpl_Factory();
  }
}

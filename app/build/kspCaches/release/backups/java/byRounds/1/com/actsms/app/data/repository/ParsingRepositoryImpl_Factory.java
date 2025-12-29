package com.actsms.app.data.repository;

import com.actsms.app.data.parsing.SmsParserImpl;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ParsingRepositoryImpl_Factory implements Factory<ParsingRepositoryImpl> {
  private final Provider<SmsParserImpl> smsParserProvider;

  public ParsingRepositoryImpl_Factory(Provider<SmsParserImpl> smsParserProvider) {
    this.smsParserProvider = smsParserProvider;
  }

  @Override
  public ParsingRepositoryImpl get() {
    return newInstance(smsParserProvider.get());
  }

  public static ParsingRepositoryImpl_Factory create(Provider<SmsParserImpl> smsParserProvider) {
    return new ParsingRepositoryImpl_Factory(smsParserProvider);
  }

  public static ParsingRepositoryImpl newInstance(SmsParserImpl smsParser) {
    return new ParsingRepositoryImpl(smsParser);
  }
}

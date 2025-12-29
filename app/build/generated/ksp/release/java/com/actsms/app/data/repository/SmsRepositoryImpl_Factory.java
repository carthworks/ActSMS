package com.actsms.app.data.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SmsRepositoryImpl_Factory implements Factory<SmsRepositoryImpl> {
  private final Provider<Context> contextProvider;

  public SmsRepositoryImpl_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SmsRepositoryImpl get() {
    return newInstance(contextProvider.get());
  }

  public static SmsRepositoryImpl_Factory create(Provider<Context> contextProvider) {
    return new SmsRepositoryImpl_Factory(contextProvider);
  }

  public static SmsRepositoryImpl newInstance(Context context) {
    return new SmsRepositoryImpl(context);
  }
}

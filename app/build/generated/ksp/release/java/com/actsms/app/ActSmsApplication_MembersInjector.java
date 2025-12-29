package com.actsms.app;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ActSmsApplication_MembersInjector implements MembersInjector<ActSmsApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public ActSmsApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<ActSmsApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new ActSmsApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(ActSmsApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.actsms.app.ActSmsApplication.workerFactory")
  public static void injectWorkerFactory(ActSmsApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}

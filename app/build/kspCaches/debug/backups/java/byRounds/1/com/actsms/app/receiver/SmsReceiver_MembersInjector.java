package com.actsms.app.receiver;

import com.actsms.app.domain.usecase.ProcessSmsUseCase;
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
public final class SmsReceiver_MembersInjector implements MembersInjector<SmsReceiver> {
  private final Provider<ProcessSmsUseCase> processSmsUseCaseProvider;

  public SmsReceiver_MembersInjector(Provider<ProcessSmsUseCase> processSmsUseCaseProvider) {
    this.processSmsUseCaseProvider = processSmsUseCaseProvider;
  }

  public static MembersInjector<SmsReceiver> create(
      Provider<ProcessSmsUseCase> processSmsUseCaseProvider) {
    return new SmsReceiver_MembersInjector(processSmsUseCaseProvider);
  }

  @Override
  public void injectMembers(SmsReceiver instance) {
    injectProcessSmsUseCase(instance, processSmsUseCaseProvider.get());
  }

  @InjectedFieldSignature("com.actsms.app.receiver.SmsReceiver.processSmsUseCase")
  public static void injectProcessSmsUseCase(SmsReceiver instance,
      ProcessSmsUseCase processSmsUseCase) {
    instance.processSmsUseCase = processSmsUseCase;
  }
}

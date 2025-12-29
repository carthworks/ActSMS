package com.actsms.app.domain.usecase;

import com.actsms.app.domain.repository.ActionRepository;
import com.actsms.app.domain.repository.ParsingRepository;
import com.actsms.app.domain.repository.PreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ProcessSmsUseCase_Factory implements Factory<ProcessSmsUseCase> {
  private final Provider<ParsingRepository> parsingRepositoryProvider;

  private final Provider<ActionRepository> actionRepositoryProvider;

  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public ProcessSmsUseCase_Factory(Provider<ParsingRepository> parsingRepositoryProvider,
      Provider<ActionRepository> actionRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.parsingRepositoryProvider = parsingRepositoryProvider;
    this.actionRepositoryProvider = actionRepositoryProvider;
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public ProcessSmsUseCase get() {
    return newInstance(parsingRepositoryProvider.get(), actionRepositoryProvider.get(), preferencesRepositoryProvider.get());
  }

  public static ProcessSmsUseCase_Factory create(
      Provider<ParsingRepository> parsingRepositoryProvider,
      Provider<ActionRepository> actionRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new ProcessSmsUseCase_Factory(parsingRepositoryProvider, actionRepositoryProvider, preferencesRepositoryProvider);
  }

  public static ProcessSmsUseCase newInstance(ParsingRepository parsingRepository,
      ActionRepository actionRepository, PreferencesRepository preferencesRepository) {
    return new ProcessSmsUseCase(parsingRepository, actionRepository, preferencesRepository);
  }
}

package com.actsms.app.domain.usecase;

import com.actsms.app.domain.repository.ActionRepository;
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
public final class ManageActionUseCase_Factory implements Factory<ManageActionUseCase> {
  private final Provider<ActionRepository> actionRepositoryProvider;

  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public ManageActionUseCase_Factory(Provider<ActionRepository> actionRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.actionRepositoryProvider = actionRepositoryProvider;
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public ManageActionUseCase get() {
    return newInstance(actionRepositoryProvider.get(), preferencesRepositoryProvider.get());
  }

  public static ManageActionUseCase_Factory create(
      Provider<ActionRepository> actionRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new ManageActionUseCase_Factory(actionRepositoryProvider, preferencesRepositoryProvider);
  }

  public static ManageActionUseCase newInstance(ActionRepository actionRepository,
      PreferencesRepository preferencesRepository) {
    return new ManageActionUseCase(actionRepository, preferencesRepository);
  }
}

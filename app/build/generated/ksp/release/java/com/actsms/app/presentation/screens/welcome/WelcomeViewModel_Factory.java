package com.actsms.app.presentation.screens.welcome;

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
public final class WelcomeViewModel_Factory implements Factory<WelcomeViewModel> {
  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public WelcomeViewModel_Factory(Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public WelcomeViewModel get() {
    return newInstance(preferencesRepositoryProvider.get());
  }

  public static WelcomeViewModel_Factory create(
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new WelcomeViewModel_Factory(preferencesRepositoryProvider);
  }

  public static WelcomeViewModel newInstance(PreferencesRepository preferencesRepository) {
    return new WelcomeViewModel(preferencesRepository);
  }
}

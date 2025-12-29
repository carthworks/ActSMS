package com.actsms.app.presentation.screens.dashboard;

import com.actsms.app.domain.usecase.GetActionsUseCase;
import com.actsms.app.domain.usecase.ManageActionUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetActionsUseCase> getActionsUseCaseProvider;

  private final Provider<ManageActionUseCase> manageActionUseCaseProvider;

  public DashboardViewModel_Factory(Provider<GetActionsUseCase> getActionsUseCaseProvider,
      Provider<ManageActionUseCase> manageActionUseCaseProvider) {
    this.getActionsUseCaseProvider = getActionsUseCaseProvider;
    this.manageActionUseCaseProvider = manageActionUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getActionsUseCaseProvider.get(), manageActionUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetActionsUseCase> getActionsUseCaseProvider,
      Provider<ManageActionUseCase> manageActionUseCaseProvider) {
    return new DashboardViewModel_Factory(getActionsUseCaseProvider, manageActionUseCaseProvider);
  }

  public static DashboardViewModel newInstance(GetActionsUseCase getActionsUseCase,
      ManageActionUseCase manageActionUseCase) {
    return new DashboardViewModel(getActionsUseCase, manageActionUseCase);
  }
}

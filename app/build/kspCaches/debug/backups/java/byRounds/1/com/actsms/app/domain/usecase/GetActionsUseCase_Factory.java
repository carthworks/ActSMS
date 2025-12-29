package com.actsms.app.domain.usecase;

import com.actsms.app.domain.repository.ActionRepository;
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
public final class GetActionsUseCase_Factory implements Factory<GetActionsUseCase> {
  private final Provider<ActionRepository> actionRepositoryProvider;

  public GetActionsUseCase_Factory(Provider<ActionRepository> actionRepositoryProvider) {
    this.actionRepositoryProvider = actionRepositoryProvider;
  }

  @Override
  public GetActionsUseCase get() {
    return newInstance(actionRepositoryProvider.get());
  }

  public static GetActionsUseCase_Factory create(
      Provider<ActionRepository> actionRepositoryProvider) {
    return new GetActionsUseCase_Factory(actionRepositoryProvider);
  }

  public static GetActionsUseCase newInstance(ActionRepository actionRepository) {
    return new GetActionsUseCase(actionRepository);
  }
}

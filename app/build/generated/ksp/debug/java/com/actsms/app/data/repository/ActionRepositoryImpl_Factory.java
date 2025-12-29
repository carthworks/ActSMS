package com.actsms.app.data.repository;

import com.actsms.app.data.local.dao.ActionDao;
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
public final class ActionRepositoryImpl_Factory implements Factory<ActionRepositoryImpl> {
  private final Provider<ActionDao> actionDaoProvider;

  public ActionRepositoryImpl_Factory(Provider<ActionDao> actionDaoProvider) {
    this.actionDaoProvider = actionDaoProvider;
  }

  @Override
  public ActionRepositoryImpl get() {
    return newInstance(actionDaoProvider.get());
  }

  public static ActionRepositoryImpl_Factory create(Provider<ActionDao> actionDaoProvider) {
    return new ActionRepositoryImpl_Factory(actionDaoProvider);
  }

  public static ActionRepositoryImpl newInstance(ActionDao actionDao) {
    return new ActionRepositoryImpl(actionDao);
  }
}

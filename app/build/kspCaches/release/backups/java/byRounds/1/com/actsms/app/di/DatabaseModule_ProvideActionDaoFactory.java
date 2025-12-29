package com.actsms.app.di;

import com.actsms.app.data.local.ActSmsDatabase;
import com.actsms.app.data.local.dao.ActionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideActionDaoFactory implements Factory<ActionDao> {
  private final Provider<ActSmsDatabase> databaseProvider;

  public DatabaseModule_ProvideActionDaoFactory(Provider<ActSmsDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ActionDao get() {
    return provideActionDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideActionDaoFactory create(
      Provider<ActSmsDatabase> databaseProvider) {
    return new DatabaseModule_ProvideActionDaoFactory(databaseProvider);
  }

  public static ActionDao provideActionDao(ActSmsDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideActionDao(database));
  }
}

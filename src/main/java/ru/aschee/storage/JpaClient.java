package ru.aschee.storage;

import ru.aschee.storage.data.CompanyData;
import ru.aschee.storage.data.ContactData;
import ru.aschee.storage.data.UserData;

public interface JpaClient extends ContactData, UserData, CompanyData {
}

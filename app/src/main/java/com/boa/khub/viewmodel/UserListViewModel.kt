package com.boa.khub.viewmodel

import com.boa.khub.repository.UserRepository
import io.reactivex.Observable
import com.boa.khub.viewmodel.data.UsersList
import timber.log.Timber

class UserListViewModel(private val userRepository: UserRepository) {

    fun getUsers(): Observable<UsersList> {
        //Create the data for your UI, the users lists and maybe some additional data needed as well
        return userRepository.getUsers()
                .map {
                    Timber.d("Mapping users to UIData...")
                    UsersList(it.take(10), "Top 10 Users")
                }
                .onErrorReturn {
                    Timber.d("An error occurred $it")
                    UsersList(emptyList(), "An error occurred", it)
                }
    }
}

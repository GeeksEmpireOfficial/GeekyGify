/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/20/20 12:35 PM
 * Last modified 2/20/20 12:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Networking.ServerConnections

import android.content.Context
import androidx.preference.PreferenceManager
import net.geeksempire.geeky.gify.R

class RemoteConfigFunctions (var context: Context) {

    fun joinedBetaProgram(joinedValue: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
            this.putBoolean("JoinedBetaProgrammer", joinedValue)
            this.apply()
        }
    }
    private fun joinedBetaProgram(): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean("JoinedBetaProgrammer", false)
    }

    fun versionCodeRemoteConfigKey(): String {
        var versionCodeKey: String? = null
        versionCodeKey = if (joinedBetaProgram()) {
            context.getString(R.string.betaIntegerVersionCodeNewUpdateWatch)
        } else {
            context.getString(R.string.integerVersionCodeNewUpdateWatch)
        }
        return versionCodeKey
    }

    fun versionNameRemoteConfigKey(): String {
        var versionCodeKey: String? = null
        versionCodeKey = if (joinedBetaProgram()) {
            context.getString(R.string.betaStringVersionNameNewUpdateWatch)
        } else {
            context.getString(R.string.stringVersionNameNewUpdateWatch)
        }
        return versionCodeKey
    }

    fun upcomingChangeLogRemoteConfigKey(): String {
        var versionCodeKey: String? = null
        versionCodeKey = if (joinedBetaProgram()) {
            context.getString(R.string.betaStringUpcomingChangeLogWatch)
        } else {
            context.getString(R.string.stringUpcomingChangeLogWatch)
        }
        return versionCodeKey
    }

    fun upcomingChangeLogSummaryConfigKey(): String {
        var versionCodeKey: String? = null
        versionCodeKey = if (joinedBetaProgram()) {
            context.getString(R.string.betaStringUpcomingChangeLogSummaryWatch)
        } else {
            context.getString(R.string.stringUpcomingChangeLogSummaryWatch)
        }
        return versionCodeKey
    }
}
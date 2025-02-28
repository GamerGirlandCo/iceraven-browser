/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components.appstate.shopping

import org.mozilla.fenix.components.appstate.AppAction.ShoppingAction
import org.mozilla.fenix.components.appstate.AppState
import org.mozilla.fenix.components.appstate.shopping.ShoppingState.CardState

/**
 * Reducer for the shopping state that handles [ShoppingAction]s.
 */
internal object ShoppingStateReducer {

    /**
     * Reduces the given [ShoppingAction] into a new [AppState].
     */
    fun reduce(state: AppState, action: ShoppingAction): AppState =
        when (action) {
            is ShoppingAction.ShoppingSheetStateUpdated -> state.copy(
                shoppingState = state.shoppingState.copy(
                    shoppingSheetExpanded = action.expanded,
                ),
            )

            is ShoppingAction.HighlightsCardExpanded -> {
                val updatedValue =
                    state.shoppingState.productCardState[action.productPageUrl]?.copy(
                        isHighlightsExpanded = action.expanded,
                    ) ?: CardState(isHighlightsExpanded = action.expanded)

                state.copy(
                    shoppingState = state.shoppingState.updateProductCardState(
                        action.productPageUrl,
                        updatedValue,
                    ),
                )
            }

            is ShoppingAction.InfoCardExpanded -> {
                val updatedValue =
                    state.shoppingState.productCardState[action.productPageUrl]?.copy(isInfoExpanded = action.expanded)
                        ?: CardState(isInfoExpanded = action.expanded)

                state.copy(
                    shoppingState = state.shoppingState.updateProductCardState(
                        action.productPageUrl,
                        updatedValue,
                    ),
                )
            }

            is ShoppingAction.SettingsCardExpanded -> {
                val updatedValue =
                    state.shoppingState.productCardState[action.productPageUrl]?.copy(
                        isSettingsExpanded = action.expanded,
                    ) ?: CardState(isSettingsExpanded = action.expanded)

                state.copy(
                    shoppingState = state.shoppingState.updateProductCardState(
                        action.productPageUrl,
                        updatedValue,
                    ),
                )
            }
        }

    private fun ShoppingState.updateProductCardState(key: String, value: CardState): ShoppingState =
        copy(productCardState = productCardState + (key to value))
}

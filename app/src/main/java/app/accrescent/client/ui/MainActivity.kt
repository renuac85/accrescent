package app.accrescent.client.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.accrescent.client.data.db.App
import app.accrescent.client.ui.theme.AccrescentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccrescentTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent(viewModel: MainViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState(snackbarHostState = viewModel.snackbarHostState)

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RefreshButton()
            AppList()
        }
    }
}

@Composable
fun RefreshButton(viewModel: MainViewModel = viewModel()) {
    Button(onClick = { viewModel.refreshRepoData() }, Modifier.padding(12.dp)) {
        Text("Refresh")
    }
}

@Composable
fun AppList(viewModel: MainViewModel = viewModel()) {
    val apps by viewModel.apps.collectAsState(emptyList())

    LazyColumn {
        items(apps) {
            InstallableAppCard(it)
        }
    }
}

@Composable
fun InstallableAppCard(app: App, viewModel: MainViewModel = viewModel()) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                app.name,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.h4,
            )
            Button(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                onClick = { viewModel.installApp(app.id) },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
            ) {
                Text("Install", color = Color.LightGray)
            }
        }
    }
}

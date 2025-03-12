package com.example.smart_meeting.screens

import UserInfoViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userInfoViewModel: UserInfoViewModel,
    onEditProfile: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // 用户基本信息卡片
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 用户头像
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "用户头像",
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 用户名
                    Text(
                        text = "你好！ "+userInfoViewModel.getFullName(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    if (userInfoViewModel.reginstered == false) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // 编辑资料按钮
                        FilledTonalButton(
                            onClick = { showEditDialog = true },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("登陆/注册")
                        }
                    }
                }
            }
        }

        // 用户详细信息列表
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    ProfileInfoItem(title = "姓", content = userInfoViewModel.lastName)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoItem(title = "名", content = userInfoViewModel.firstName)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoItem(title = "年龄", content = userInfoViewModel.age.toString())
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoItem(title = "邮箱", content = userInfoViewModel.email)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoItem(title = "手机", content = userInfoViewModel.phoneNumber.toString())
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoItem(title = "部门", content = userInfoViewModel.department)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ProfileInfoItem(title = "职位", content = userInfoViewModel.position)
                }
            }
        }

        // 其他选项卡片保持不变
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "更多选项",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ProfileActionItem(
                        title = "添加好友",
                        items = listOf("个人名片", "面对面开会")
                    )
                }
            }
        }
    }

    // 编辑资料对话框
    if (showEditDialog) {
        EditProfileDialog(
            userInfoViewModel = userInfoViewModel,
            onDismiss = { showEditDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileDialog(
    userInfoViewModel: UserInfoViewModel,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "编辑个人资料",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = userInfoViewModel.lastName,
                    onValueChange = { userInfoViewModel.lastName = it },
                    label = { Text("姓") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userInfoViewModel.firstName,
                    onValueChange = { userInfoViewModel.firstName = it },
                    label = { Text("名") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userInfoViewModel.age.toString(),
                    onValueChange = {
                        if (!it.isEmpty() && it.matches(Regex("^\\d*\$"))) {
                        userInfoViewModel.phoneNumber.countryCode = it.toInt()
                    } },
                    label = { Text("年龄") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userInfoViewModel.email,
                    onValueChange = { userInfoViewModel.email = it },
                    label = { Text("邮箱") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 手机号输入（包含国家代码）
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = userInfoViewModel.phoneNumber.countryCode.toString(),
                        onValueChange = {
                            if (!it.isEmpty() && it.matches(Regex("^\\d*\$"))) {
                                userInfoViewModel.phoneNumber.countryCode = it.toInt()
                            }
                        },
                        label = { Text("国家") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(0.3f)
                    )

                    OutlinedTextField(
                        value = userInfoViewModel.phoneNumber.number.toString(),
                        onValueChange = {
                            if (!it.isEmpty() && it.matches(Regex("^\\d*\$"))) {
                                userInfoViewModel.phoneNumber.number = it.toLong()
                            }
                        },
                        label = { Text("手机号码") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.weight(0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userInfoViewModel.department,
                    onValueChange = { userInfoViewModel.department = it },
                    label = { Text("部门") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userInfoViewModel.position,
                    onValueChange = { userInfoViewModel.position = it },
                    label = { Text("职位") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("取消")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            // TODO: 这里需要在 UserInfoViewModel 中添加更新用户信息的方法
                            // userInfoViewModel.updateUserInfo(...)
                            userInfoViewModel.reginstered = true
                            onDismiss()
                        }
                    ) {
                        Text("保存")
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoItem(title: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ProfileActionItem(
    title: String,
    items: List<String> = emptyList()
) {
    // ProfileActionItem 的实现保持不变
    var isExpanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        label = "rotation"
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isExpanded = !isExpanded
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .graphicsLayer { rotationZ = rotationState },
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AnimatedVisibility(
            visible = isExpanded && items.isNotEmpty(),
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                // 处理子项点击事件
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (item != items.last()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }
        }
    }
}
# git-push.ps1

Write-Host "========== Start Git Operation Script ==========" -ForegroundColor Cyan

function Run-GitCommand {
    param (
        [string]$Command,
        [string]$ErrorMessage
    )

    Write-Host ">>> $Command" -ForegroundColor Yellow
    Invoke-Expression $Command

    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ $ErrorMessage" -ForegroundColor Red
        exit $LASTEXITCODE
    }
}

Run-GitCommand "git push origin master" "Push origin master failed"
Run-GitCommand "git push gitee master" "Push gitee master failed"

Write-Host "========== Git Operation Script Completed ==========" -ForegroundColor Green
pause

let timer
let handlerVideo = () => {
    console.log("触发定时器")
    let button = document.querySelector(".mvp-toggle-play")
    let video = document.querySelector("video")
    if (button && video) {
        video.playbackRate = 16
        video.muted = true
        button.click()
        video.onended = () => {
            console.log("视频播放完毕");
            document.querySelector("a.next").click();
            timer = setInterval(handlerVideo, 1500)
        }
        clearInterval(timer)
    }
}

(() => {
    timer = setInterval(handlerVideo, 1500)
})()
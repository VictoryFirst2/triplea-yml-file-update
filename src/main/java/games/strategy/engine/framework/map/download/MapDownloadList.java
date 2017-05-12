package games.strategy.engine.framework.map.download;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import games.strategy.util.Version;

class MapDownloadList {

  private final List<DownloadFileDescription> available = new ArrayList<>();
  private final List<DownloadFileDescription> installed = new ArrayList<>();
  private final List<DownloadFileDescription> outOfDate = new ArrayList<>();

  MapDownloadList(final List<DownloadFileDescription> downloads, final FileSystemAccessStrategy strategy) {
    for (final DownloadFileDescription download : downloads) {
      if (download == null) {
        return;
      }
      final Optional<Version> mapVersion = strategy.getMapVersion(download.getInstallLocation().getAbsolutePath());

      if (mapVersion.isPresent()) {
        installed.add(download);
        if (download.getVersion() != null && download.getVersion().isGreaterThan(mapVersion.get())) {
          outOfDate.add(download);
        }
      } else {
        available.add(download);
      }
    }
  }

  List<DownloadFileDescription> getAvailable() {
    return available;
  }

  List<DownloadFileDescription> getInstalled() {
    return installed;
  }

  List<DownloadFileDescription> getOutOfDate() {
    return outOfDate;
  }
}

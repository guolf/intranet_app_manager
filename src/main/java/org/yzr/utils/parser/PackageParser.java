package org.yzr.utils.parser;

import org.yzr.model.Package;
import org.yzr.utils.PathManager;

public interface PackageParser {
    // 解析包
    public Package parse(PathManager pathManager, String filePath);
}
